import {createCookie} from "react-router";

export const userCookie = createCookie("user", {
    path: "/",
    httpOnly: false,
    secure: true,
    maxAge: 60,
});