/*
 * @author zhujunhan
 * @date 2020/12/11
 */
new Vue({
    el: '#app',
    data() {
        return {
            isCollapsed: false,
            title: '首页',
            activeMenuName: sessionStorage.activeMenuName || 'home',
            openMenuName: sessionStorage.openMenuName ? [sessionStorage.openMenuName] : [],
            menus: [
                {
                    title: '首页',
                    name: 'home',
                    icon: 'ios-home',
                    href: '/'
                },
                {
                    title: '我参与的项目',
                    name: 'projects',
                    icon: 'ios-cube',
                    href: '/projects'
                },
                {
                    title: '我的合并请求',
                    name: 'merge-requests',
                    icon: 'md-git-pull-request',
                    children: []
                },
                {
                    title: '统计信息',
                    name: 'report-manage',
                    icon: 'ios-stats',
                    children: [
                        {
                            title: '统计报表',
                            name: 'about22',
                            href: '/home'
                        }
                    ]
                }
            ]
            // ------------------------------  菜单操作结束  --------------------------------
        }
    },
    mounted() {
        const submenus = this.menus.find(item => item.name === 'merge-requests');
        submenus.children = [];
        projects.forEach(proj => {
            submenus.children.push({
                title: proj.name,
                name: 'merge-menu-' + proj.id,
                href: `/merge-requests/${proj.id}`,
                icon: 'md-git-branch'
            })
        })
    },
    computed: {
        rotateIcon() {
            return [
                'menu-icon',
                this.isCollapsed ? 'rotate-icon' : ''
            ];
        },
        menuitemClasses() {
            return [
                'menu-item',
                this.isCollapsed ? 'collapsed-menu' : ''
            ]
        }
    },
    // ------------------------------  菜单操作结束  --------------------------------
    methods: {
        quit() {
            location.href = '/logout';
        },
        collapsedSider() {
            this.$refs.sidebar.toggleCollapse();
        },
        choosedMenu(name) {
            // 设置选中菜单name
            sessionStorage.activeMenuName = name;

            //根据name查找对应的菜单对象
            let menu = null;
            this.menus.forEach(_menu => {
                if (_menu.name === name) {
                    menu = _menu;
                    sessionStorage.openMenuName = null;
                } else if (_menu.children) {
                    _menu.children.forEach(child => {
                        if (child.name === name) {
                            menu = child;
                            sessionStorage.openMenuName = _menu.name;
                        }
                    })
                }
            })
            location.href = menu.href;
        },
        dropdownClick(name) {
            this.choosedMenu(name);
        }
        // ------------------------------  菜单操作结束  --------------------------------
    }
});
