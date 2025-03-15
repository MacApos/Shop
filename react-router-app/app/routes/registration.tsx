import React from 'react';
import type {Route} from "./+types/registration";
import {EntityEnum, create} from "~/data";

import {Form, redirect, useNavigate, useFetcher} from "react-router";
import ValidatedForm from "~/common/ValidatedForm";
import ValidatedInput from "~/common/ValidatedInput";

export async function action({request}: Route.ActionArgs) {
    const formData = await request.formData();
    const user = Object.fromEntries(formData);
    const response = await create(user, EntityEnum.USER);
    if (response.ok) {
        return redirect("/");
    }
    return response;
}

export default function Registration({actionData}: Route.ComponentProps) {

    return (
        <>
            {/*<div className="modal" tabIndex="-1">*/}
            {/*    <div className="modal-dialog">*/}
            {/*        <div className="modal-content">*/}
            {/*            <div className="modal-header">*/}
            {/*                <h5 className="modal-title">Modal title</h5>*/}
            {/*                <button type="button" className="btn-close" data-bs-dismiss="modal"*/}
            {/*                        aria-label="Close"></button>*/}
            {/*            </div>*/}
            {/*            <div className="modal-body">*/}
            {/*                <p>Modal body text goes here.</p>*/}
            {/*            </div>*/}
            {/*            <div className="modal-footer">*/}
            {/*                <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>*/}
            {/*                <button type="button" className="btn btn-primary">Save changes</button>*/}
            {/*            </div>*/}
            {/*        </div>*/}
            {/*    </div>*/}
            {/*</div>*/}

            <ValidatedForm>
                <ValidatedInput>
                    <input name={"username"} defaultValue={"bigZbig"}/>
                </ValidatedInput>
                <ValidatedInput>
                    <input name={"firstname"} defaultValue={"Zbigniew"}/>
                </ValidatedInput>
                <ValidatedInput>
                    <input name={"lastname"} defaultValue={"Nowak"}/>
                </ValidatedInput>
                <ValidatedInput>
                    <input name={"email"} type={"email"} defaultValue={"u1326546@gmail.com"}/>
                </ValidatedInput>
                <ValidatedInput>
                    <input name={"password"} type={"password"} defaultValue={"Nowak"}/>
                </ValidatedInput>
                <ValidatedInput label={"Confirm password"}>
                    <input name={"passwordConfirm"} type={"password"} defaultValue={"Nowak"}/>
                </ValidatedInput>
            </ValidatedForm>
        </>
    );
}