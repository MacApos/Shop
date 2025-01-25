import {useState} from 'react';

export function useInput(initialState: { [key: string]: string }) {
    const [state, setState] = useState(initialState);
    const input: { [key: string]: any } = {};
    for (const key of Object.keys(initialState)) {
        const name = key;
        input[name] = {
            name,
            value: state[name],
            onChange: (e: { target: { value: any; }; }) => setState(prevState => {
                return {
                    ...prevState,
                    [name]: e.target.value
                };
            })
        };
    }
    return [state, input];
}