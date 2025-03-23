import React from 'react'
import {decrement, increment, selectCount} from './counterSlice';
import {useAppDispatch, useAppSelector} from "~/hooks";

export function Counter() {
    const count = useAppSelector(selectCount)
    const dispatch = useAppDispatch()

    return (
        <div>
            <div>
                <button
                    aria-label="Increment value"
                    onClick={() => dispatch(increment())}
                >
                    Increment
                </button>
                <span>{count}</span>
                <button
                    aria-label="Decrement value"
                    onClick={() => dispatch(decrement())}
                >
                    Decrement
                </button>
            </div>
        </div>
    )
}