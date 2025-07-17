import {redirect} from "react-router";
import type {Route} from "./+types/logout";
import {findCookieByName, jwtToken} from "~/cookie.server";

export async function loader({request}: Route.LoaderArgs) {
    const serializedCookie = findCookieByName(request, "jwt");
    if (!serializedCookie) {
        return redirect("/");
    }
    const jwt = await jwtToken.parse(serializedCookie);
    const jwtCookie = await jwtToken.serialize(jwt, {maxAge: 0});
    return redirect("/", {
        headers: {
            "Set-Cookie": jwtCookie
        }
    });
}