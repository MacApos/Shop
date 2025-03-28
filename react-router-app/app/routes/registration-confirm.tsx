import React from 'react';
import type {Route} from "./+types/registration-confirm";
import {EntityEnum, confirm} from "~/data";
import {redirect} from "react-router";

export async function loader({request}: Route.LoaderArgs) {
    const url = new URL(request.url);
    const token = url.searchParams.get("token");
    if (!token) {
        throw new Response("Not Found", {status: 404});
    }
    const response = await confirm(EntityEnum.USER, token);
    if (response.ok) {
        return redirect("/");
    }
    return {response};
}

export default function RegistrationConfirm({loaderData}: Route.ComponentProps) {
    const {response} = loaderData;
    return (
        <>
            <div>
                    <div>
                        Token expired
                        ### resend verification link ###
                    </div>
                    <div>
                        Token invalid
                    </div>
            </div>
        </>
    );
}