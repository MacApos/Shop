import React, {useEffect, useRef, useState} from 'react';

export type ValidatedInputProps = {
    label?: string;
    defaultValue?: string | any;
    defaultError?: string[];
    serverSideError?: string[];
    validated?: boolean;
    showBeforeValidation?: boolean;
    validationFunction?: (value: string) => string[];
    revokeValidation?: () => void;
    children: React.ReactElement<HTMLInputElement>;
}

export default function ValidatedInput(props: ValidatedInputProps) {
    const {
        children, defaultError, serverSideError, validated, showBeforeValidation,
        validationFunction, revokeValidation
    } = props;

    const childProps = children.props;
    const name = childProps.name;
    const minLength = childProps.minLength ?? 3;

    const label = props.label ?? name.charAt(0).toUpperCase() + name.slice(1).toLowerCase();
    const invalidInput = `Invalid ${label.toLowerCase()}`;

    const [value, setValue] = useState(childProps.defaultValue ?? "");
    const [errorMessages, setErrorMessages] = useState<string[]>();
    const [infoFeedback, setInfoFeedback] = useState<string>();
    const [feedbackMessage, setFeedbackMessage] = useState<string>();
    const inputRef = useRef<HTMLInputElement>(null);

    useEffect(() => {
        const input = inputRef.current;
        if (input && serverSideError) {
            input.setCustomValidity(invalidInput);
            setErrorMessages(serverSideError);
            if (revokeValidation) {
                setFeedbackMessage("d-block");
            }
        }
    }, [serverSideError]);

    useEffect(() => {
        if (revokeValidation) {
            return;
        }

        const input = inputRef.current;
        if (!input) {
            return;
        }

        if (validationFunction) {
            const validationErrors = validationFunction(value);
            input.setCustomValidity(validationErrors.length ? invalidInput : "");
            setErrorMessages(validationErrors);
            return;
        }

        const {validity} = input;
        if (validity.valid) {
            return;
        }

        if (defaultError?.length) {
            setErrorMessages(defaultError);
        } else {
            let message = invalidInput;
            if (validity.tooShort) {
                message = `${label} must be at least ${minLength} characters long.`;
            }
            setErrorMessages([message]);
        }
    }, [value]);

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const targetValue = event.currentTarget.value;
        setValue(targetValue);

        if (serverSideError && revokeValidation) {
            revokeValidation();
        } else if (serverSideError) {
            event.currentTarget.setCustomValidity("");
        }
    };

    return (
        <div>
            <label className={"form-label"}>
                {label}
            </label>
            <input
                ref={inputRef}
                value={value}
                name={name}
                type={childProps.type ?? "text"}
                required={childProps.required ?? true}
                minLength={minLength}
                className={"form-control"}
                onChange={handleChange}
                onFocus={() => setInfoFeedback(showBeforeValidation && !validated ? "info-feedback" : "")}
                onBlur={() => setInfoFeedback("")}
            />

            {errorMessages?.map((error, index) => {
                    return (
                        <div key={name + "error" + index}
                             className={`invalid-feedback ${infoFeedback} ${feedbackMessage}`}>
                            {error}
                        </div>
                    );
                }
            )}
        </div>
    );
}