import {combineReducers, configureStore} from "@reduxjs/toolkit";
import userReducer from "./features/userSlice";
import modalReducer from "./features/modalSlice";

export const store = configureStore({
    reducer: {
        user: userReducer,
        modal: modalReducer
    }
});

export type AppStore = typeof store
export type RootState = ReturnType<AppStore["getState"]>
export type AppDispatch = AppStore["dispatch"]