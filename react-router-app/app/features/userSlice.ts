import {createSlice, type PayloadAction} from "@reduxjs/toolkit";
import type {RootState} from "~/store";

interface UserState {
    username: string,
    firstname: string,
    lastname: string,
    email: string,
    password: string
}

export const userSlice = createSlice({
    name: "user",
    initialState: {},
    reducers: {
        setUser: (_, action: PayloadAction<UserState>) => {
            return action.payload;
        },
    },
});

export const {setUser} = userSlice.actions;
export const selectUser = (state: RootState) => state.user;
export default userSlice.reducer;
