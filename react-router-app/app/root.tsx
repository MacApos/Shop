import {
    isRouteErrorResponse,
    Links,
    Meta,
    Outlet, redirect,
    Scripts,
    ScrollRestoration,
} from "react-router";

import type {Route} from "./+types/root";
import logo from './logo.svg';
import app from "../../src/main/resources/static/css/App.css?url";
import index from "../../src/main/resources/static/css/index.css?url";
import bootstrap from "../../src/main/resources/static/css/bootstrap-pulse.css?url";

export const links: Route.LinksFunction = () => [
    {rel: "stylesheet", href: app},
    {rel: "stylesheet", href: index},
    {rel: "stylesheet", href: bootstrap},
];

export default function App() {
    (() => {
        'use strict'

        // Fetch all the forms we want to apply custom Bootstrap validation styles to
        // const forms = document.querySelectorAll('.needs-validation')

        // Loop over them and prevent submission
        // Array.from(forms).forEach(form => {
        //     form.addEventListener('submit', event => {
        //         if (!form.checkValidity()) {
        //             event.preventDefault()
        //             event.stopPropagation()
        //         }
        //
        //         form.classList.add('was-validated')
        //     }, false)
        // })
    })()

    return (
        <Outlet/>
    )
}

export function Layout({children}: { children: React.ReactNode }) {
    return (
        <html lang="en">
        <head>
            <meta charSet="utf-8"/>
            <meta name="viewport" content="width=device-width, initial-scale=1"/>
            <Meta/>
            <Links/>
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
            <div id="loading-splash-spinner" />
            <p>Loading, please wait...</p>
        </div>
    );
}

export function ErrorBoundary({error}: Route.ErrorBoundaryProps) {
    let message = "Oops!";
    let details = "An unexpected error occurred.";
    let stack: string | undefined;

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
        <main className="pt-16 p-4 container mx-auto">
            <h1>{message}</h1>
            <p>{details}</p>
            {stack && (
                <pre className="w-full p-4 overflow-x-auto">
          <code>{stack}</code>
        </pre>
            )}
        </main>
    );
}
