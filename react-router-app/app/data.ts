const url = "http://localhost:8080";

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

export const defaultInit: { [key: string]: any } = {
    method: "GET",
    headers: {
        "Content-Type": "application/json",
    },
};

async function fetchData(path: string, init = defaultInit) {
    const response = await fetch(url + path, init);
    const json = await response.json();
    return {
        body: json,
        status: response.status,
        ok: response.ok
    };
}

export async function confirm(entity: EntityEnum, token: string) {
    return await fetchData(`/${entity}/${CONFIRM}?token=${token}`);
}

export async function login(body: Record<string, any>) {
    return await fetchData("/login",
        {
            headers: {
                "Content-Type": "application/json",
            },
            credentials: "include",
            method: "POST",
            body: JSON.stringify(body),
        });
}

export async function secretToken(body: Record<string, any>) {
    return await fetchData("/secrete-token",
        {
            ...defaultInit,
            credentials: "include",
            method: "POST",
            body: JSON.stringify(body),
        });
}


export async function createEntity(body: { [key: string]: any }, entity: EntityEnum) {
    return await fetchData(`/${entity}/${CREATE}`,
        {
            ...defaultInit,
            body: JSON.stringify(body),
            method: "POST"
        });
}

export async function updateEntity(body: { [key: string]: any }, entity: EntityEnum, id: Number) {
    return await fetchData(`/${entity}/${UPDATE}/${id}`,
        {
            ...defaultInit,
            body: JSON.stringify(body),
            method: "PUT"
        });
}

export async function deleteEntity(entity: EntityEnum, id: Number) {
    return await fetchData(`/${entity}/${DELETE}/${id}`,
        {
            ...defaultInit,
            method: "DELETE"
        });
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