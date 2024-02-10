import { zodResolver } from "@hookform/resolvers/zod";
import axios from "axios";
import { useForm } from "react-hook-form";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import * as zod from "zod";
import { SignInRequestModel, SignUpRequestModel } from "../../Models/UserModel";
import { signInAction } from "../../Redux/Slices/AuthSlice";
import notificationService, { ErrorDetails } from "../../Services/NotificationService";
import webApiService from "../../Services/WebApiService";
import "./SignInUpForm.css";

function SignInUpForm(): JSX.Element {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const schema = zod.object({
    username: zod.string().email({ message: "Invalid email address" }),
    password: zod.string().min(4, { message: "Password must be at least 4 characters long" }),
  });

  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<SignInRequestModel | SignUpRequestModel>({
    mode: "onSubmit",
    resolver: zodResolver(schema),
  });

  const onSubmit = async (data: SignInRequestModel | SignUpRequestModel, isSignIn: boolean) => {
    try {
      if (isSignIn) {
        // SignIn logic
        // Go to coupon server (gateway) use are webapi service
        const res = await webApiService.signInApi(data as SignInRequestModel);
        notificationService.successPlainText("SignIn successfully");
        // Save
        dispatch(signInAction(res.token));
        navigate("/store");
      } else {
        // SignUp logic
        await webApiService.signUpApi(data as SignUpRequestModel);
        notificationService.successPlainText("Registered successfully");
      }
    } catch (error) {
      if (axios.isAxiosError<ErrorDetails>(error)) {
        notificationService.errorAxiosApiCall(error);
      } else {
        notificationService.errorPlainText('An unknown error occurred');
      }
    }
  };

  return (
    <div className="SignInUpForm">
      <form className="SignInUpForm">
        <h2>SignIn | SignUp Form</h2>
        {/* Username (Email) */}
        <label htmlFor="username">Username (Email):</label>
        <input {...register("username")} id="username" name="username" type="email" placeholder="Email here" />
        <span className="form-error-message"> {errors.username?.message}</span>

        {/* Password */}
        <label htmlFor="password">Password:</label>
        <input
          {...register("password")}
          id="password"
          name="password"
          type="password"
          placeholder="Password here"
        />
        <span className="form-error-message"> {errors.password?.message}</span>

        {/* Buttons */}
        <div className="SignInUpFormButtons">
          <button type="button" onClick={handleSubmit((data) => onSubmit(data, true))} disabled={isSubmitting}>
            SignIn
          </button>
          <button type="button" onClick={handleSubmit((data) => onSubmit(data, false))} disabled={isSubmitting}>
            SignUp
          </button>
        </div>
      </form>
    </div>
  );
}

export default SignInUpForm;