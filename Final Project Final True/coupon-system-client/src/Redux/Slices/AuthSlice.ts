import { createSlice } from '@reduxjs/toolkit';
import { jwtDecode } from 'jwt-decode';

interface AuthState {
  token: string;
  decodedToken: {
    uuid: string,
    username: string,
    exp: number,
  };
}

const initialState: AuthState = {
  token: localStorage.getItem('token') || "",
  decodedToken: localStorage.getItem('decodedToken') ? JSON.parse(localStorage.getItem('decodedToken') || "") : {
    uuid: '',
    username: 'guest',
    exp: 0,
  }
}

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    signInAction(state, action) {
      try {
        state.token = action.payload;
        localStorage.setItem('token', state.token);
        state.decodedToken = jwtDecode(state.token);
        localStorage.setItem('decodedToken', JSON.stringify(state.decodedToken));
      } catch (error) {
        console.error('Error storing token in localStorage:', error);
      }
    },

    signOutAction(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('decodedToken');
      state.token = "";
      state.decodedToken = {
        uuid: '',
        username: 'guest',
        exp: 0,
      }
    },
  },
});

export const { signInAction, signOutAction } = authSlice.actions;
export default authSlice.reducer;
