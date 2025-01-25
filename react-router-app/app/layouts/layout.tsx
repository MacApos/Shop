import React from 'react';
import {Form, Outlet} from "react-router";

export default function Layout() {
    return (
        <>
            <Form action="login">
                <button type="submit">Login</button>
            </Form>
            <Form action="registration">
                <button type="submit">Register</button>
            </Form>
            <Outlet/>
        </>
    );
}