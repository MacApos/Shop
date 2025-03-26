import React, {useState} from 'react';
import {Form, useNavigate} from "react-router";
import ValidatedInput from "~/common/ValidatedInput";

type ValidatedFormProps = {
    children: React.ReactElement<typeof ValidatedInput> |
        React.ReactElement<typeof ValidatedInput>[],
    action?: string
    actionData?: Record<string,
        Record<string, string | string[] | object> |
        undefined>
    shouldRevokeValidation?: boolean
}

export default function ValidatedForm(props: ValidatedFormProps) {
    const {children, actionData, action, shouldRevokeValidation} = props;

    const navigate = useNavigate();
    const [validated, setValidated] = useState(false);

    const revokeValidation = () => {
        setValidated(false);
    };

    let childrenWithProps:any[] = React.Children.map(children, child => {
        let props = {};
        if ("showBeforeValidation" in child.props && child.props.showBeforeValidation) {
            props = {...props, validated};
        }
        if (shouldRevokeValidation) {
            props = {...props, revokeValidation};
        }
        return Object.keys(props).length ? child : React.cloneElement(child, props);
    });

// when serverSideErrors appear
    if (actionData?.errors) {
        const {errors} = actionData;
        childrenWithProps = childrenWithProps.map(child => {
            // if (child.props && "children" in child.props &&
            //     child.props.children && child.props.children instanceof Object && "props" in child.props.children &&
            //     child.props.children.props && child.props.children.props instanceof Object && "name" in child.props.children.props &&
            //     child.props.children.props.name === "string" && child.props.children.props.name in errors) {
            //     const name = child.props.children.props.name;
            //     const props = {serverSideError: errors[name]};
            // }
                const name = child.props.children.props.name;
                if (name in errors) {
                    return React.cloneElement(child, {serverSideError: errors[name]});
                }
            return child;
        });
    }

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>): void => {
        const form = event.currentTarget;
        if (!form.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
        }
        setValidated(true);
    };

    return (
        <Form method="post" action={action ?? ""} className={`${validated ? "was-validated" : ""}`}
              onSubmit={handleSubmit} noValidate>
            {childrenWithProps}
            <button type={"submit"}>Submit</button>
            <button type={"button"} onClick={() => navigate(-1)}>Cancel</button>
        </Form>
    );
}