import { Ban } from "~/models/Ban";

export default defineEventHandler(async (event) => {
    const id: number = Number(getRouterParam(event, 'id'));
    if (!id) {
        throw createError({ status: 400, message: 'id is missing or invalid'});
    }
    
    return await $fetch<Ban>(`${useRuntimeConfig(event)?.public?.api?.url}/api/v1/bans/${id}/entry`);
});