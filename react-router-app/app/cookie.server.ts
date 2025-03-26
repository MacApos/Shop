import {createCookie} from "react-router";

const cookieOptions = {
    path: "/",
    httpOnly: true,
    secure: true,
    maxAge: 7 * 24 * 60 * 60,
};

const jwtToken = createCookie("jwt", cookieOptions);
const userCookie = createCookie("user", cookieOptions);

export {jwtToken, userCookie};