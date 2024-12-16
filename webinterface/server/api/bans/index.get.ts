import { Ban } from "~/models/Ban";
import { PaginatedResponse } from "~/models/PaginatedResponse";

export default defineEventHandler(async (event) => {
    const page: number = Number(getQuery(event)?.page) ?? 0;
    const pageSize: number = Number(getQuery(event)?.pageSize) ?? 25;
    const type: string = getQuery(event)?.type as string;

    return await $fetch<PaginatedResponse<Ban>>(`${useRuntimeConfig(event)?.public?.api?.url}/api/v1/bans`, {
        query: { type, page, pageSize }
    });
});