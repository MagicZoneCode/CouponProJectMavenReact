import moment from "moment";
import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { CouponModel } from "../../Models/CouponModel";
import store from "../../Redux/Store";
import "./ViewCoupon.css";


function ViewCoupon(): JSX.Element {
  const { uuid } = useParams();

  const [coupon, setCoupon] = useState<CouponModel | undefined>(store.getState().coupon.myCoupons.find((c) => c.uuid === uuid));

  const navigation = useNavigate();

  function handleBack() {
    navigation("/my-coupons")
  }
  return (
    <div className="ViewCoupon">
      {
        coupon ?
          <div>
            <img src={coupon?.image} alt="coupon" />
            <p>Title: {coupon?.title}</p>
            <p>Description: {coupon?.description}</p>
            <p>Amount: {coupon?.amount}</p>
            <p>Price: {coupon?.price}</p>
            <p>Start Date: {moment(coupon?.startDate).format("DD/MM/YYYY")}</p>
            <p>End Date: {moment(coupon?.endDate).format("DD/MM/YYYY")}</p>
            <p>Category: {coupon?.category}</p>
          </div>
          :
          <div>coupon not found</div>
      }
      <button onClick={handleBack}>Back</button>
    </div>
  );
}

export default ViewCoupon;
