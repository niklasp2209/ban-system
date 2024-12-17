export const formatDate = (timestamp: number): string => {
    const date: Date = new Date(timestamp);
    return `${date?.getDate()}.${date?.getMonth() + 1}.${date?.getFullYear()}`;
}