import React, {useEffect} from 'react';
import type {Route} from "./+types/registration-confirm";
import {confirm, Entity, fetching, Method} from "~/data";
import MessageComponent from "~/common/MessageComponent";
import {data, NavLink, useNavigate} from "react-router";
import {cookieOptionsToString, jwtToken} from "~/cookie.server";
import {useAppDispatch} from "~/hooks";
import {setShow} from "~/features/modalSlice";

export async function loader({request}: Route.LoaderArgs) {
    const url = new URL(request.url);
    const token = url.searchParams.get("token");
    if (!token) {
        throw new Response("Not Found", {status: 404});
    }
    const response = await confirm(Entity.USER, token);
    if (response.ok) {

        const jwtCookie = await jwtToken.serialize(response.body.jwt);
        return data({token, errors: undefined}, {
            headers: {
                "Set-Cookie": jwtCookie
            }
        });
    }
    return {token, errors: response.body};
}

export default function RegistrationConfirm({loaderData}: Route.ComponentProps) {
    const {token, errors} = loaderData;
    const dispatch = useAppDispatch();
    const navigate = useNavigate();

    const handleClick = async (e: React.MouseEvent<HTMLAnchorElement>) => {
        e.preventDefault();
        e.stopPropagation();
        dispatch(setShow(true));
        const response = await fetching(`/${Entity.USER}/resend-registration-token?token=${token}`);
        if (!response.ok) {
            navigate(`/registration-confirm%token=${token}`, {replace: true});
        }
        navigate("/", {replace: true});
    };

    const message = errors ? undefined : "Good job!";
    const details = errors ?
        "Your token is invalid or has already been used." : "expiryDate" in errors ?
            <>
                Your token has expired, click <a href={""} onClick={handleClick}>here</a> to resend activation link
                to your email address.
            </> :
            <>
                Your account has been activated, now return to <NavLink to={"/"} replace>home page →</NavLink>
            </>;
    return (
        <MessageComponent message={message} details={details}/>
    );
}