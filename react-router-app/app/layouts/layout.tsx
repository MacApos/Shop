import React, {useEffect, useState} from 'react';
import {Form, Outlet} from "react-router";
import {selectUser} from "~/features/userSlice";
import {useAppSelector} from "~/hooks";
import {selectModal} from "~/features/modalSlice";

declare const bootstrap: any;

export default function Layout() {
    const user = useAppSelector(selectUser);
    const modal = useAppSelector(selectModal);

    let userElement =
        <>
            <p>This is Layout</p>
            <Form action="login">
                <button type="submit">Login</button>
            </Form>
            <Form action="registration">
                <button type="submit">Register</button>
            </Form>
        </>;

    if (user && "username" in user && typeof user.username === "string") {
        userElement = <div>Howdy {user.username}</div>;
    }

    useEffect(() => {
        const modalElement = new bootstrap.Modal("#modalSheet");
        if (modal.show) {
            modalElement.show();
        }
    }, [modal]);

    return (
        <>
            <div className="modal fade" id="modalSheet" data-bs-backdrop="static" data-bs-keyboard="false"
                 tabIndex={-1}>
                <div className="modal-dialog modal-dialog-centered">
                    <div className="modal-content rounded-4 shadow">
                        <div className="modal-header">
                            <h1 className="modal-title fs-5" id="exampleModalLabel">Account registered</h1>
                        </div>
                        <div className="modal-body">
                            Please confirm registration with link send to your email.
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-lg btn-primary" data-bs-dismiss="modal">
                                Close
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            {userElement}
            <Outlet/>
        </>
    );
}