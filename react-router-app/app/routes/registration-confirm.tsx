import React from 'react';
import type {Route} from "./+types/registration-confirm"
import {Entity, confirm} from "~/data"

export async function loader({request, params}: Route.LoaderArgs) {
    console.log(params.token)
    const url = new URL(request.url);
    const token = url.searchParams.get("token");
    if (!token) {
        throw new Response("Not Found", {status: 404});
    }
    await confirm(Entity.USER, token);
    return {token}
}

export default function RegistrationConfirm({loaderData}: Route.ComponentProps) {
    const {token} = loaderData;
    return (
        <>
            test
        </>
    );
}