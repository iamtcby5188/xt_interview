import request from '@/utils/request'

// 认证相关 API
export const authApi = {
  login(data: { username: string; password: string }): Promise<any> {
    return request.post('/auth/login', data)
  },
  logout(): Promise<any> {
    return request.post('/auth/logout')
  },
  validate(): Promise<any> {
    return request.get('/auth/validate')
  }
}

// 账号管理 API
export const accountApi = {
  getAll(): Promise<any> {
    return request.get('/accounts')
  },
  getById(id: number): Promise<any> {
    return request.get(`/accounts/${id}`)
  },
  create(data: any): Promise<any> {
    return request.post('/accounts', data)
  },
  update(id: number, data: any): Promise<any> {
    return request.put(`/accounts/${id}`, data)
  },
  delete(id: number): Promise<any> {
    return request.delete(`/accounts/${id}`)
  },
  getPage(params: { page: number; size: number; username?: string; status?: number; roleLevel?: number }): Promise<any> {
    return request.get('/accounts/page', { params })
  }
}

// 部门管理 API
export const departmentApi = {
  getAll(): Promise<any> {
    return request.get('/departments')
  },
  getById(id: number): Promise<any> {
    return request.get(`/departments/${id}`)
  },
  create(data: any): Promise<any> {
    return request.post('/departments', data)
  },
  update(id: number, data: any): Promise<any> {
    return request.put(`/departments/${id}`, data)
  },
  delete(id: number): Promise<any> {
    return request.delete(`/departments/${id}`)
  }
}
