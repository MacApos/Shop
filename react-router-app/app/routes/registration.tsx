import React, {useEffect} from 'react';
import {type ActionFunctionArgs, Navigate, useNavigation} from "react-router";
import type {Route} from "./+types/registration";
import {useAppDispatch} from "~/hooks";
import {setShow} from "~/features/modalSlice";
import {Entity, createEntity} from "~/data";
import ValidatedForm from "~/common/ValidatedForm";
import ValidatedInput from "~/common/ValidatedInput";

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
    const response = await createEntity(data, Entity.USER);
    return {response};
}

export default function Registration({actionData}: Route.ComponentProps) {
    const response = actionData?.response;
    const body = response?.body;
    const ok = response?.ok;
    const navigation = useNavigation();
    const dispatch = useAppDispatch();

    useEffect(() => {
        if (ok) {
            dispatch(setShow(true));
        }
    }, [response]);

    if (ok) {
        return <Navigate to={"/"} replace/>;
    }

    return (
        <div className={"w-75 m-auto"}>
            {navigation.state === "submitting" &&
                <div className="spinner-border" role="status">
                    <span className="visually-hidden">Loading...</span>
                </div>
            }
            <ValidatedForm errors={body}>
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
                                defaultError={["Invalid password"]}>
                    <input name={"password"}
                           defaultValue={"P@ssword456"}
                    />
                </ValidatedInput>
                <ValidatedInput label={"Confirm password"} defaultError={["Invalid password confirmation"]}>
                    <input name={"passwordConfirm"} minLength={0}
                           defaultValue={"P@ssword456"}
                    />
                </ValidatedInput>
            </ValidatedForm>
        </div>
    );
}