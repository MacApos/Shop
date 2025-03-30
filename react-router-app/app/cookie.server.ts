import {createCookie} from "react-router";

const cookieOptions: Record<string, string | boolean | number> = {
    path: "/",
    httpOnly: true,
    secure: true,
    maxAge: 7 * 24 * 60 * 60,
};

export const cookieToString = () => {
    return Object.keys(cookieOptions).map(key => `${key}=${cookieOptions[key]};`).join(" ");
};

const jwtToken = createCookie("jwt", cookieOptions);
const userCookie = createCookie("user", cookieOptions);

export {jwtToken, userCookie};