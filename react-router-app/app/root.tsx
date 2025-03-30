import {
    isRouteErrorResponse,
    Outlet,
    Scripts,
    ScrollRestoration,
} from "react-router";

import type {Route} from "./+types/root";
import appStylesHref from "../../src/main/resources/static/css/app.css?url";
import favicon from "./public/favicon.ico?url";
import {Provider} from "react-redux";
import {store} from './store';
import MessageComponent from "~/common/MessageComponent";

export default function App() {
    return (
        <Provider store={store}>
            <Outlet/>
        </Provider>
    );
}

export function Layout({children}: { children: React.ReactNode }) {
    return (
        <html lang="en">
        <head>
            <meta charSet="utf-8"/>
            <meta name="viewport" content="width=device-width, initial-scale=1"/>
            <link rel="stylesheet" href={appStylesHref}/>
            <script src="../node_modules/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
            <link rel="icon" href={favicon}/>
        </head>
        <body>
        {children}
        <ScrollRestoration/>
        <Scripts/>
        </body>
        </html>
    );
}

export function HydrateFallback() {
    return (
        <div id="loading-splash">
            <div id="loading-splash-spinner"/>
            <p>Loading, please wait...</p>
        </div>
    );
}

export function ErrorBoundary({error}: Route.ErrorBoundaryProps) {
    let message;
    let details;
    let stack;

    if (isRouteErrorResponse(error)) {
        message = error.status === 404 ? "404" : "Error";
        details =
            error.status === 404
                ? "The requested page could not be found."
                : error.statusText || details;
    } else if (import.meta.env.DEV && error && error instanceof Error) {
        details = error.message;
        stack = error.stack;
    }

    return (
        <>
            <MessageComponent message={message} details={details} stack={stack}/>
        </>
    );
}