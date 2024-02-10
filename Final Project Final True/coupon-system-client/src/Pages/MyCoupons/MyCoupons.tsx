import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import CouponCard from "../../Components/CouponCard/CouponCard";
import { CouponModel } from "../../Models/CouponModel";
import { setAllCouponsAction, setMyCouponsAction } from "../../Redux/Slices/CouponSlice";
import store from "../../Redux/Store";
import notificationService from "../../Services/NotificationService";
import webApiService from "../../Services/WebApiService";
import "./MyCoupons.css";

function MyCoupons(): JSX.Element {
  const [myCoupons, setMyCoupons] = useState<CouponModel[]>(store.getState().coupon.myCoupons);
  const dispatch = useDispatch();

  useEffect(() => {
    if (myCoupons.length === 0) {
      webApiService.getMyCouponsApi().then((res) => {
        dispatch(setMyCouponsAction(res))
        setMyCoupons(res)
        notificationService.successPlainText("Fetch My Coupons")
      }).catch((error) => {
        notificationService.errorAxiosApiCall(error);
      })
    }
    if (store.getState().coupon.allCoupons.length === 0) {
      webApiService.getAllCouponsApi().then((res) => {
        dispatch(setAllCouponsAction(res))
      }).catch((error) => {
        notificationService.errorAxiosApiCall(error);
      })
    }
  }, [])

  return (
    <div className="MyCoupons">
      {myCoupons.length > 0 ? myCoupons.map((c) => {
        return <CouponCard key={c.uuid} coupon={c} isAvailable={false} />
      }) : "No Coupons to show yet"}
    </div>
  );
}

export default MyCoupons;
