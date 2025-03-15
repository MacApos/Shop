const url = "http://localhost:8080";

const defaultInit: { [key: string]: any } = {
    method: "GET",
    headers: {
        "Content-Type": "application/json",
    }
};

export const CREATE = "create";
export const READ = "get";
export const UPDATE = "update";
export const DELETE = "delete";
export const CONFIRM = "confirm-registration";

export enum EntityEnum {
    CATEGORY = "category",
    PRODUCT = "product",
    USER = "user",
}

async function fetchData(path: string, init = defaultInit) {
    const response = await fetch(url + path, init);
    const json = await response.json();
    return {
        body: json,
        status: response.status,
        ok: response.ok
    };
}

export async function getCategories(id: number) {
    return await fetchData(id === null ? "/category/all" : `/category/${id}`);
}

export async function getCategory(id: number) {
    return await fetchData(`/category/${id}`);
}

export async function create(body: { [key: string]: any }, entity: EntityEnum) {
    return await fetchData(`/${entity}/${CREATE}`,
        {
            ...defaultInit,
            body: JSON.stringify(body),
            method: "POST"
        });
}

export async function update(body: { [key: string]: any }, entity: EntityEnum, id: Number) {
    return await fetchData(`/${entity}/${UPDATE}/${id}`,
        {
            ...defaultInit,
            body: JSON.stringify(body),
            method: "POST"
        });
}

export async function confirm(entity: EntityEnum, token: string) {
    return await fetchData(`/${entity}/${CONFIRM}?token=${token}`);
}

export function normalize(text: string) {
    return text.split(" ").map((t, i) => {
        t = t.toLowerCase();
        if (i !== 0) {
            return t.charAt(0).toLowerCase() + t.slice(1);
        }
        return t;
    }).join()
        .normalize("NFKC")
        .replaceAll("\\p{M}", "")
        .replaceAll("[\\u0141-\\u0142]", "l");
}