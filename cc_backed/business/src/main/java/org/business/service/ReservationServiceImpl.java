package org.business.service;

import ch.qos.logback.core.util.StringUtil;
import org.business.exceptions.NotEnoughSpotsException;
import org.business.model.Reservation;
import org.business.model.Restaurant;
import org.business.pojo.ReservationDto;
import org.business.repository.ReservationRepository;
import org.business.repository.RestaurantRepository;
import org.business.utils.AppUtils;
import org.business.utils.PageableResponse;
import org.service.customer.model.Customer;
import org.service.customer.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;


    public ReservationServiceImpl(ReservationRepository reservationRepository, CustomerRepository customerRepository, RestaurantRepository restaurantRepository) {
        this.reservationRepository = reservationRepository;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
    }

    final RestaurantRepository restaurantRepository;

    @Override
    public PageableResponse<ReservationDto> findReservations(Pageable pageable, String searchString) {
        pageable = AppUtils.enhancePageable(pageable);
        Page<Reservation> reservationPage = StringUtil.isNullOrEmpty(searchString) ?
                reservationRepository.findAll(pageable) :
                reservationRepository.searchReservationByGuestNameLike(searchString, pageable);

        List<ReservationDto> payload = reservationPage.map(reservation -> ReservationDto.builder()
                        .id(reservation.getId())
                        .guestCount(reservation.getGuestCount())
                        .reservationDate(reservation.getReservationDate())
                        .restaurantName(reservation.getRestaurantName())
                        .reservationGuestName(reservation.getGuestName())
                        .build()
                )
                .stream().toList();

        return AppUtils.buildPageableResponse(payload, reservationPage);
    }


    @Override
    public ReservationDto createNewReservation(Integer customerId,
                                               Integer restaurantId,
                                               ReservationDto reservationDto) {
        Restaurant restaurant = restaurantRepository.loadRestaurantByIdWithReservation(restaurantId)
                .orElse(null);
        if (restaurant == null) {
            throw new RuntimeException("Could not find restaurant with id: " + restaurantId);
        }

        boolean enoughSpotsForRestaurant = isEnoughSpotsForRestaurant(reservationDto, restaurant);
        if (!enoughSpotsForRestaurant) {
            throw new NotEnoughSpotsException("Not enough spots for booking");
        }

        Customer customer = null;
        if (customerId != null) {
            customer = customerRepository.findById(customerId)
                    .orElse(null);
        }

        Reservation reservation = new Reservation();
        if (customer != null) {
            reservation.setCustomer(customer);
        }
        reservation.setGuestCount(reservationDto.getGuestCount());
        reservation.setReservationDate(reservationDto.getReservationDate());
        reservation.setGuestName(customer != null ? customer.getName() : reservationDto.getReservationGuestName());
        reservation.setGuestCount(reservationDto.getGuestCount());
        reservation.setRestaurantName(restaurant.getName());

        int remainingAvailableSpots = restaurant.getAvailableSpots() - reservationDto.getGuestCount();
        restaurant.setAvailableSpots(remainingAvailableSpots);
        restaurant.addReservation(reservation);

        Restaurant newRestaurant = restaurantRepository.save(restaurant);

        reservationDto.setId(newRestaurant.getId());

        return reservationDto;
    }

    public boolean manageReservation(HttpMethod currentMethod,
                                     Integer reservationId,
                                     ReservationDto reservationDto) {
        if (HttpMethod.DELETE.equals(currentMethod)) {
            reservationRepository.deleteById(reservationId);
            return true;
        }
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElse(null);

        if (reservation == null) {
            throw new RuntimeException("Could not find reservation with id: " + reservationId);
        }
        reservation.setGuestCount(reservationDto.getGuestCount());
        reservation.setReservationDate(reservationDto.getReservationDate());
        reservationRepository.save(reservation);
        return true;
    }

    private boolean isEnoughSpotsForRestaurant(ReservationDto newReservation,
                                               Restaurant currentRestaurant) {
        if (newReservation.getGuestCount() <= currentRestaurant.getAvailableSpots()) {
            return true;
        }
        return false;
    }

}