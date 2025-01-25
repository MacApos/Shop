import {type RouteConfig, layout, index, route} from "@react-router/dev/routes";

export default [
    layout("layouts/layout.tsx", [
        index("routes/home.tsx"),
        route("registration", "routes/registration.tsx"),
        route("confirm-registration", "routes/registration-confirm.tsx")
    ])
] satisfies RouteConfig;
