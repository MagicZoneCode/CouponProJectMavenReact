import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { signOutAction } from "../../Redux/Slices/AuthSlice";
import { clearCouponAction } from "../../Redux/Slices/CouponSlice";
import "./SignOut.css";

function SignOut(): JSX.Element {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(signOutAction());
    dispatch(clearCouponAction());
    navigate("/sign-in");
  }, []);

  return (
    <div className="SignOut">

    </div>
  );
}

