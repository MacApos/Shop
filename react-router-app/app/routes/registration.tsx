import React, {useEffect} from 'react';
import type {Route} from "./+types/registration";
import {EntityEnum, createEntity} from "~/data";

import {type ActionFunctionArgs, useNavigate} from "react-router";
import ValidatedForm from "~/common/ValidatedForm";
import ValidatedInput from "~/common/ValidatedInput";
import {useAppDispatch} from "~/hooks";
import {setUser} from "~/features/userSlice";

const passwordValidation = (value: string) => {
    const minLength = 8;
    const maxLength = 16;
    const minNumberOfChars = 1;

    const requirements = [
        {regex: new RegExp(`^.{0,${maxLength}}$`), message: `at most ${maxLength} characters`},
        {regex: new RegExp(`.{${minLength},}`), message: `at least ${minLength} characters`},
        {regex: /[A-Z]/, message: `at least ${minNumberOfChars} uppercase characters`},
        {regex: /[a-z]/, message: `at least ${minNumberOfChars} lowercase characters`},
        {regex: /\d/, message: `at least ${minNumberOfChars} number`},
        {
            regex:
                /[!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~¡¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿×÷–—―‗‘’‚‛“”„†‡•…‰′″‹›‼‾⁄⁊₠₡₢₣₤₥₦₧₨₩₪₫€₭₮₯₰₱₲₳₴₵₶₷₸₹₺₻₼₽₾]/,
            message: `at least ${minNumberOfChars} special character`
        }
    ];

    const errorsArr: string[] = [];
    requirements.forEach(({regex, message}) => {
        if (!regex.test(value)) {
            errorsArr.push(`must contain ${message}`);
        }
    });

    return errorsArr;
};

export const emailValidation = (value: string) => {
    return /^[A-Za-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/.test(value) ? [] : ["Invalid email"];
};

export async function action({request}: ActionFunctionArgs) {
    const formData = await request.formData();
    const data = Object.fromEntries(formData);
    const response = await createEntity(data, EntityEnum.USER);
    return {data, response};
}

export default function Registration({actionData}: Route.ComponentProps) {
    const response = actionData?.response;
    const body = response?.body;
    const navigate = useNavigate();
    const dispatch = useAppDispatch();

    useEffect(() => {
        if (response && response.ok) {
            dispatch(setUser(body));
            navigate("/");
        }
    }, [response]);


    return (
        <div className={"w-75 m-auto"}>
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

            <ValidatedForm actionData={response && !response.ok ? {data: actionData.data, errors: body} : undefined}>
                <ValidatedInput>
                    <input name={"username"}
                           defaultValue={"bigZbig"}
                    />
                </ValidatedInput>
                <ValidatedInput>
                    <input name={"firstname"}
                           defaultValue={"Zbigniew"}
                    />
                </ValidatedInput>
                <ValidatedInput>
                    <input name={"lastname"}
                           defaultValue={"Nowak"}
                    />
                </ValidatedInput>
                <ValidatedInput
                    validationFunction={emailValidation}>
                    <input name={"email"} type={"email"}
                           defaultValue={"u1326546@gmail.com"}
                    />
                </ValidatedInput>
                <ValidatedInput validationFunction={passwordValidation} showBeforeValidation={true}
                                defaultError={["Invalid password"]}
                >
                    <input name={"password"}
                           defaultValue={"P@ssword456"}
                    />
                </ValidatedInput>
                <ValidatedInput label={"Confirm password"} defaultError={["Invalid password confirmation"]}>
                    <input name={"passwordConfirm"} minLength={0}
                           // defaultValue={"P@ssword45"}
                    />
                </ValidatedInput>
            </ValidatedForm>
        </div>
    );
}