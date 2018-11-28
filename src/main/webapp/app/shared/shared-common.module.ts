import { NgModule } from '@angular/core';

import { QuikzSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [QuikzSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [QuikzSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class QuikzSharedCommonModule {}
