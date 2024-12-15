export interface PaginatedResponse<T> {
    totalPages: number,
    content: T[]
}