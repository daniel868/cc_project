package org.business.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Objects;

public class AppConstants {

    public static final List<WhiteListAPI> WHITE_LIST_APIS = List.of(
            new WhiteListAPI(HttpMethod.GET, "/restaurants"),
            new WhiteListAPI(HttpMethod.GET, "/reservations"),
            new WhiteListAPI(HttpMethod.POST, "/reservations"),
            new WhiteListAPI(HttpMethod.PUT, "/reservations"),
            new WhiteListAPI(HttpMethod.DELETE, "/reservations")
    );

    @Getter
    @Setter
    @AllArgsConstructor
    public static class WhiteListAPI {
        private HttpMethod method;
        private String url;

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            WhiteListAPI that = (WhiteListAPI) o;
            return Objects.equals(method, that.method) && Objects.equals(url, that.url);
        }

        @Override
        public int hashCode() {
            return Objects.hash(method, url);
        }
    }
}
