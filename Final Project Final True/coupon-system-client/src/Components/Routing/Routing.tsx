import { Route, Routes } from "react-router-dom";
import App from "../../App";
import MyCoupons from "../../Pages/MyCoupons/MyCoupons";
import NotFound from "../../Pages/NotFound/NotFound";
import Store from "../../Pages/Store/Store";
import ViewCoupon from "../../Pages/ViewCoupon/ViewCoupon";
import SignInUpForm from "../SignInUpForm/SignInUpForm";
import "./Routing.css";

function Routing(): JSX.Element {

  return (
    <div className="Routing">
      <Routes>
        <Route path="/" element={<App />} />
        <Route index element={<SignInUpForm />} />
        <Route path="/store" element={<Store />} />
        <Route path="/my-coupons" element={<MyCoupons />} />
        <Route path="/sign-in" element={<SignInUpForm />} />
        <Route path="/sign-up" element={<SignInUpForm />} />
        <Route path="/my-coupons/:uuid" element={<ViewCoupon />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </div>
  );
}

export default Routing;
