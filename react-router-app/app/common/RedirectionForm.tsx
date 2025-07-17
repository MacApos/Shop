import React from 'react';
import {Form} from "react-router";

export default function RedirectionForm({action, text}:{action:string, text:string}) {
    return (
        <Form action={action}>
            <button type="submit">{text}</button>
        </Form>
    );
}