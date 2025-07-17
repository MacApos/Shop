import React, {useEffect} from 'react';
import {Form, Outlet} from "react-router";
import {useAppDispatch, useAppSelector} from "~/hooks";
import {selectModal, setShow} from "~/features/modalSlice";
import {Entity, fetching} from "~/data";
import {cookieOptionsToString, findCookieByName, jwtToken} from "~/cookie.server";
import type {Route} from "./+types/layout";
import NavigationBar from "~/common/NavigationBar";
import Modal from "~/common/Modal";

declare const bootstrap: any;

export async function loader({request}: Route.LoaderArgs) {
    const serializedCookie = findCookieByName(request, "jwt");
    if (!serializedCookie) {
        return {user: undefined};
    }
    const jwt = await jwtToken.parse(serializedCookie);
    const jwtCookie = `jwt=${jwt}; ${cookieOptionsToString()}`;
    const response = await fetching(`/${Entity.USER}`, {
        headers: {
            "Cookie": jwtCookie
        }
    });
    return {user: response.ok ? response.body : undefined};
}

export default function Layout({loaderData}: Route.ComponentProps) {
    const {user} = loaderData;
    const dispatch = useAppDispatch();
    const modal = useAppSelector(selectModal);

    useEffect(() => {
        const modalElement = new bootstrap.Modal("#modalSheet");
        if (modal.show) {
            modalElement.show();
        }
    }, [modal]);

    return (
        <>
            <Modal header={"Account registered"} body = {"Please confirm registration with link send to your email."}
                   footerButton={"Close"} handleClick={()=>dispatch(setShow(false))}/>
            <p>This is Layout</p>
            <NavigationBar user={user}/>
            <Outlet/>
        </>
    );
}