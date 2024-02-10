// import { useState } from "react";
// import { CouponModel } from "../../Models/CouponModel";
import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import CouponCard from "../../Components/CouponCard/CouponCard";
import { CouponModel } from "../../Models/CouponModel";
import { setAllCouponsAction } from "../../Redux/Slices/CouponSlice";
import store from "../../Redux/Store";
import notificationService from "../../Services/NotificationService";
import webApiService from "../../Services/WebApiService";
import "./Store.css";

function Store(): JSX.Element {
  const [allCoupons, setAllCoupons] = useState<CouponModel[]>(store.getState().coupon.allCoupons);
  const dispatch = useDispatch();

  useEffect(() => {
    const unsubscribe = store.subscribe(() => {
      setAllCoupons(store.getState().coupon.allCoupons)
    })

    if (allCoupons.length === 0) {
      webApiService.getAllCouponsApi().then((res) => {
        dispatch(setAllCouponsAction(res))
        setAllCoupons(res)
        notificationService.successPlainText("Fetch All Coupons")
      }).catch((error) => {
        notificationService.errorAxiosApiCall(error);
      })
    }


    return () => {
      unsubscribe()
    }
  }, [])

  return (
    <div className="Store">
      {allCoupons.length > 0 ? allCoupons.map((c) => {
        return <CouponCard key={c.uuid} coupon={c} isAvailable={true} />
      }) : "No Coupons to show yet"}
    </div>
  );
}

export default Store;
