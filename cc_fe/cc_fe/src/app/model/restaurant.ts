export class Restaurant {
  name: string = '';
  imageUrl: string = '';
  description: string = '';
  address: string = '';
  maximumGuestNumber: number = 0;
  availableSpots: number = 0;

  constructor(name: string, url: string, description: string, location: string) {
    this.name = name;
    this.imageUrl = url;
    this.description = description;
    this.address = location;
  }
}
