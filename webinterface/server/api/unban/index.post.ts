import { UnbanRequest } from "~/models/UnbanRequest";

export default defineEventHandler(async (event) => {
    const body: UnbanRequest = await readBody<UnbanRequest>(event);
    const res = await $fetch.raw<void>(`${useRuntimeConfig(event)?.public?.api?.url}/api/v1/bans/unban`, {
        method: 'POST',
        body,
    });

    if (res?.status !== 204) {
        throw createError({ status: res?.status, message: res?.statusText });
    }
});