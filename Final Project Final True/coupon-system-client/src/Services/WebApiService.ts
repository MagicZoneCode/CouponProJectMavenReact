import axios, { AxiosRequestConfig } from "axios";
import { CouponModel } from "../Models/CouponModel";
import { SignInRequestModel, SignInResponseModel, SignUpRequestModel } from "../Models/UserModel";
import store from "../Redux/Store";
import globalUrlService from "./GlobalUrlService";

class WebApiService {

  private get headers(): AxiosRequestConfig {
    return { headers: { 'Authorization': store.getState().auth.token } };
  }



  public async signUpApi(signUpRequestModel: SignUpRequestModel) {
    await axios.post(globalUrlService.getBaseUrl() + "register", signUpRequestModel)
  }

  public async signInApi(signInRequestModel: SignInRequestModel) {
    const response = await axios.post(globalUrlService.getBaseUrl() + "authentication", signInRequestModel);
    const data: SignInResponseModel = {
      token: response.data
    };
    return data;
  }

  public async getAllCouponsApi(): Promise<CouponModel[]> {
    const response = await axios.get(globalUrlService.getBaseUrl() + "all-coupons", this.headers);
    const data: CouponModel[] = response.data;
    return data;
  }

  public async purchaseCouponApi(couponUuid: string): Promise<CouponModel> {
    const response = await axios.post(globalUrlService.getBaseUrl() + "buy/" + couponUuid, {}, this.headers);
    const data: CouponModel = response.data;
    return data;
  }

  public async getMyCouponsApi(): Promise<CouponModel[]> {
    const response = await axios.get(globalUrlService.getBaseUrl() + "all-customer-coupons", this.headers);
    const data: CouponModel[] = response.data;
    return data;
  }

}

const webApiService = new WebApiService();
export default webApiService;