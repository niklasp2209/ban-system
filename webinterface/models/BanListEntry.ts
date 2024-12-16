export interface BanListEntry {
    id: number,
    player: string,
    templateId: string,
    bannedBy: string,
    bannedAt: number,
    expiresAt: number,
    active: boolean,
    reason: string
}