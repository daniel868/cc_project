export interface ROLE {
  roleName: string
}


export function isAdmin(role: ROLE): boolean {
  return role.roleName === 'ADMIN'
}
