export class Restaurant {
  name: string = '';
  url: string = '';
  description: string = '';
  location: string = '';

  constructor(name: string, url: string, description: string, location: string) {
    this.name = name;
    this.url = url;
    this.description = description;
    this.location = location;
  }
}
