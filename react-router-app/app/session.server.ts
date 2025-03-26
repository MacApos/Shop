import {createCookieSessionStorage} from "react-router";

type SessionData = {
    token: string;
    user: {
        name: string;
        email: string;
    };
};

type SessionFlashData = {
    error: string;
};

const {getSession, commitSession, destroySession} =
    createCookieSessionStorage<SessionData, SessionFlashData>(
        {
            cookie: {
                name: "__session",
                httpOnly: true,
                secure: true,
                maxAge: 7 * 24 * 60 * 60,
                path: "/",
                sameSite: "lax",
                secrets: ["s3cret1"],
            },
        }
    );

export {getSession, commitSession, destroySession};
