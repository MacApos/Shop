import React, {useEffect} from 'react';
import type {Route} from "./+types/registration-confirm";
import {confirm, Entity, fetching, Method} from "~/data";
import MessageComponent from "~/common/MessageComponent";
import {data, Navigate, NavLink, useNavigate} from "react-router";
import {cookieToString} from "~/cookie.server";
import {useAppDispatch} from "~/hooks";
import {setShow} from "~/features/modalSlice";
import {setUser} from "~/features/userSlice";

export async function loader({request}: Route.LoaderArgs) {
    const url = new URL(request.url);
    const token = url.searchParams.get("token");
    if (!token) {
        throw new Response("Not Found", {status: 404});
    }
    const response = await confirm(Entity.USER, token);
    if (response.ok) {
        const {jwt, ...body} = response.body;
        response.body = body.user;
        return data({token, response}, {
            headers: {
                "Set-Cookie": `jwt=${jwt}; ${cookieToString()}`
            }
        });
    }
    return {token, response};
}

export default function RegistrationConfirm({loaderData}: Route.ComponentProps) {
    const {token, response} = loaderData;
    const ok = response.ok;
    const body = response.body;
    const dispatch = useAppDispatch();
    const navigate = useNavigate();

    const handleClick = async (e: React.MouseEvent<HTMLAnchorElement>) => {
        e.preventDefault();
        e.stopPropagation();
        dispatch(setShow(true));
        const response = await fetching(Method.GET, `/${Entity.USER}/resend-registration-token?token=${token}`);
        if (!response.ok) {
            navigate(`/registration-confirm%token=${token}`, {replace: true});
        }
        navigate("/", {replace: true});
    };

    useEffect(() => {
        if (ok) {
            dispatch(setUser(body));
        }
    }, [response]);

    const message = ok ? "Good job!" : undefined;
    const details = ok ?
        <>
            Your account has been activated, now return to <NavLink to={"/"} replace>home page →</NavLink>
        </> : "expiryDate" in body ?
            <>
                Your token has expired, click <a href={""} onClick={handleClick}>here</a> to resend activation link
                to
                your email address.
            </> :
            "Your token is invalid or has already been used.";
    return (
        <MessageComponent message={message} details={details}/>
    );
}