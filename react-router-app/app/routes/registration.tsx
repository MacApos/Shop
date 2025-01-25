import React from 'react';
import {useInput} from "../hook/useInput";
import type {Route} from "./+types/registration"
import {Entity, create} from "~/data";

import {Form, useNavigate} from "react-router";

export async function action({request}: Route.ActionArgs) {
    const formData = await request.formData();
    const user = Object.fromEntries(formData);
    await create(user, Entity.USER);
}

export default function Registration() {
    const navigate = useNavigate();
    const [state, input] = useInput({
        username: "BigZbig",
        firstname: "Zbigniew",
        lastname: "Nowak",
        email: "zbigIsBig@gmail.com",
        password: "zbig"
    });
    let {username, firstname, lastname, email, password} = input;

    return (
        <>
            <Form method="post">
                <div>
                    <label>Username:
                        <input type={"text"} {...username}
                            // required minLength={3}
                        />
                    </label>
                </div>
                <div>
                    <label>Firstname:
                        <input type={"text"} {...firstname}
                            // required minLength={3}
                        />
                    </label>
                </div>
                <div>
                    <label>Lastname:
                        <input type={"text"} {...lastname}
                            // required minLength={3}
                        />
                    </label>
                </div>
                <div>
                    <label>Email:
                        <input type={"email"} {...email}
                               required minLength={3}
                        />
                    </label>
                </div>
                <div>
                    <label>Password: </label>
                    <input type={"password"} {...password} required/>
                </div>
                <p>
                    <button type="submit">Save</button>
                    <button onClick={() => navigate(-1)} type="button">
                        Cancel
                    </button>
                </p>
            </Form>
        </>
    );
}