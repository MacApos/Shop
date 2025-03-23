import {configureStore} from "@reduxjs/toolkit";
import counterReducer from './features/counterSlice'
import userReducer from './features/userSlice'

export const store = configureStore({
    reducer: {
        user:userReducer,
        counter:counterReducer
    }
});

export type AppStore = typeof store
export type RootState = ReturnType<AppStore['getState']>
export type AppDispatch = AppStore['dispatch']