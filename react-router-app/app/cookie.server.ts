import {type CookieOptions, createCookie} from "react-router";

type CookieOptionsType = {
    path: string,
    httpOnly: boolean,
    secure: boolean,
    maxAge: number
    credentials: boolean
}

const cookieOptions: CookieOptionsType = {
    path: "/",
    httpOnly: true,
    secure: true,
    maxAge: 7 * 24 * 60 * 60,
    credentials: true,
};

export function findCookieByName(request: Request, name: string) {
    const cookieHeader = request.headers.get("Cookie");
    return cookieHeader ? cookieHeader.split("; ").find(cookie => cookie.startsWith(name)) : undefined;
}

export function cookieOptionsToString() {
    return Object.keys(cookieOptions).map(key => `${key}=${cookieOptions[key as keyof CookieOptionsType]}`).join("; ");
}

export const jwtToken = createCookie("jwt", cookieOptions);
