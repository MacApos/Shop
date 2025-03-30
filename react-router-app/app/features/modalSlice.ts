import {createSlice, type PayloadAction} from '@reduxjs/toolkit';
import type {RootState} from '~/store';

interface ModalState {
    change: boolean,
    show: boolean,
}

const initialState: ModalState = {
    change: false,
    show: false,

};

export const modalSlice = createSlice({
    name: 'modal',
    initialState,
    reducers: {
        setShow: (state, action: PayloadAction<boolean>) => {
            state.change = !state.change;
            state.show = action.payload
        },
    }
});

export const {setShow} = modalSlice.actions;

export const selectModal = (state: RootState) => state.modal;

export default modalSlice.reducer;