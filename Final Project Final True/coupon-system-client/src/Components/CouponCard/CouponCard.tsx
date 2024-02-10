import moment from "moment";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { CouponModel } from "../../Models/CouponModel";
import { addCouponToMyCouponsAction, updateCouponInAllCouponsAction } from "../../Redux/Slices/CouponSlice";
import notificationService from "../../Services/NotificationService";
import webApiService from "../../Services/WebApiService";
import "./CouponCard.css";

interface CouponCardProps {
  coupon: CouponModel;
  isAvailable: boolean;
}

function CouponCard(props: CouponCardProps): JSX.Element {
  const dispatch = useDispatch();
  const navigation = useNavigate();

  function handlePurchaseClick() {
    webApiService.purchaseCouponApi(props.coupon.uuid).then((res) => {
      dispatch(updateCouponInAllCouponsAction(res));
      dispatch(addCouponToMyCouponsAction(res));
      notificationService.successPlainText("Coupon Purchased")
    }
    ).catch((error) => {
      notificationService.errorAxiosApiCall(error);
    })
  }

  function handleViewCoupon() {
    navigation("/my-coupons/" + props.coupon.uuid);
  }

  return (
    <div className="CouponCard">
      <img src={props.coupon.image} alt="coupon" />
      {/* <p>id: {props.coupon.uuid}</p>
      <p>companyId: {props.coupon.companyUuid}</p> */}
      <p>Title: {props.coupon.title}</p>
      <p>Description: {props.coupon.description}</p>
      <p>Amount: {props.coupon.amount}</p>
      <p>Price: {props.coupon.price}</p>
      <p>Start Date: {moment(props.coupon.startDate).format("DD/MM/YYYY")}</p>
      <p>End Date: {moment(props.coupon.endDate).format("DD/MM/YYYY")}</p>
      <p>Category: {props.coupon.category}</p>
      {props.isAvailable ?
        <button onClick={handlePurchaseClick}>Purchase</button>
        :
        <button onClick={handleViewCoupon}>View</button>}
    </div>
  );
}

export default CouponCard;
