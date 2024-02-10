import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { signOutAction } from "../../Redux/Slices/AuthSlice";
import store from "../../Redux/Store";
import "./Header.css";

function Header(): JSX.Element {
  const [decodedToken, setDecodedToken] = useState(store.getState().auth?.decodedToken);

  const navigate = useNavigate();
  const dispatch = useDispatch();


  useEffect(() => {

    const unsubscribe = store.subscribe(() => {
      setDecodedToken(store.getState().auth?.decodedToken);
    });

    return unsubscribe;
  }, []);


  const handleSignOutButton = () => {
    dispatch(signOutAction());
    navigate("/sign-in");
  }

  return (
    <div className="Header">
      <span>Hello {decodedToken.username}</span>
      {decodedToken.username === "guest" ?
        <div>
          <button onClick={() => navigate("/sign-in")}>SignIn</button>
          <button onClick={() => navigate("/sign-in")}>SignUp</button>
        </div>
        :
        <div>
          <button onClick={handleSignOutButton}>SignOut</button>
          <button onClick={() => navigate("/store")}>Store</button>
          <button onClick={() => navigate("/my-coupons")}>My Coupons</button>
        </div>
      }
    </div>
  );
}

export default Header;
