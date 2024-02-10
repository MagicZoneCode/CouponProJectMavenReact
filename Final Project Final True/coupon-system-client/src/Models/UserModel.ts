export interface UserModel {
  username: string;
  uuid: string;
}

export interface SignUpRequestModel {
  username: string;
  password: string;
}

export interface SignInRequestModel {
  username: string;
  password: string;
}

export interface SignInResponseModel {
  token: string;
}