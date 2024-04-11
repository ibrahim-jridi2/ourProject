export interface User {
    email: string;
    firstname: string;
    lastname: string;
    gender: Gender;
  }
  
  export enum Gender {
    MALE = 'MALE',
    FEMALE = 'FEMALE'
  }
